package org.example.izzy.service.impl.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.izzy.exception.ResourceNotFoundException;
import org.example.izzy.mapper.ColourVariantMapper;
import org.example.izzy.model.dto.request.admin.AdminColourVariantWithNoSizesReq;
import org.example.izzy.model.dto.request.admin.AdminEntireColourVariantReq;
import org.example.izzy.model.dto.response.admin.AdminColourVariantRes;
import org.example.izzy.model.dto.response.admin.AdminEntireColourVariantRes;
import org.example.izzy.model.entity.Category;
import org.example.izzy.model.entity.ColourVariant;
import org.example.izzy.model.entity.SizeVariant;
import org.example.izzy.repo.ColourVariantRepository;
import org.example.izzy.service.interfaces.admin.AdminColourVariantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminColourVariantServiceImpl implements AdminColourVariantService {

    private final ColourVariantMapper colourVariantMapper;
    private final ColourVariantRepository colourVariantRepository;

    @Override
    public AdminEntireColourVariantRes createEntireColourVariant(AdminEntireColourVariantReq colourVariantReq) {
        ColourVariant colourVariant = colourVariantMapper.toEntity(colourVariantReq);

        List<SizeVariant> sizeVariants = colourVariant.getSizeVariants().stream()
                .peek(sizeVariant ->
                        sizeVariant.setColourVariant(colourVariant)).toList();

        colourVariant.setSizeVariants(sizeVariants);

        ColourVariant savedcolourVariant = colourVariantRepository.save(colourVariant);

        return colourVariantMapper.toAdminEntireColourVariantRes(savedcolourVariant);
    }

    @Override
    public List<AdminEntireColourVariantRes> getEntireColourVariantsByProductId(UUID productId) {
        List<ColourVariant> colourVariantList = colourVariantRepository.findByProductId(productId);
        return colourVariantMapper.toAdminEntireColourVariantResList(colourVariantList);
    }

    @Override
    public AdminEntireColourVariantRes getOneEntireColourVariant(UUID colourVariantId) {
        ColourVariant colourVariantFromDB = getColourVariantFromDB(colourVariantId);
        return colourVariantMapper.toAdminEntireColourVariantRes(colourVariantFromDB);
    }

    @Override
    @Transactional
    public AdminColourVariantRes updateColourVariant(UUID colourVariantId, AdminColourVariantWithNoSizesReq colourVariantReq) {
        ColourVariant colourVariant = getColourVariantFromDB(colourVariantId);
        colourVariantMapper.updateColourVariantFromColourVariantReq(colourVariantReq, colourVariant);
        return colourVariantMapper.toAdminColourVariantRes(colourVariant);
    }

    @Override
    public String activateOrDeactivateColourVariant(UUID colourVariantId) {
        ColourVariant colourVariant = getColourVariantFromDB(colourVariantId);
        colourVariant.setIsActive(!colourVariant.getIsActive());
        return colourVariantRepository.save(colourVariant).getIsActive() ? "ACTIVATED✅" : "DEACTIVATED⛔";
    }

    private ColourVariant getColourVariantFromDB(UUID colourVariantId) {
        return colourVariantRepository.findById(colourVariantId).orElseThrow(() ->
                new ResourceNotFoundException("ColourVariant not found with ID: " + colourVariantId));
    }


//TEMPORARILY  DISABLED DELETING ENDPOINT SERVICE
//    @Transactional
//    @Override
//    public void deleteColourVariantWithSizes(UUID colourVariantId) {
//        if (!colourVariantRepository.existsById(colourVariantId)) {
//            throw new ResourceNotFoundException("ColourVariant not found with ID: " + colourVariantId);
//        }
//        log.error("🚮🚮Deleting colour variant with ID: {}", colourVariantId);
//        colourVariantRepository.deleteById(colourVariantId);
//        log.error("🚮🚮Deleted successfully  colour variant with ID: {} ✅✅", colourVariantId);
//    }
}
