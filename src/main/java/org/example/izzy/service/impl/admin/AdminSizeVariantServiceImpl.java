package org.example.izzy.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.example.izzy.exception.ResourceNotFoundException;
import org.example.izzy.mapper.SizeVariantMapper;
import org.example.izzy.model.dto.request.admin.AdminSizeVariantReq;
import org.example.izzy.model.dto.response.admin.AdminSizeVariantRes;
import org.example.izzy.model.entity.SizeVariant;
import org.example.izzy.repo.SizeVariantRepository;
import org.example.izzy.service.interfaces.admin.AdminSizeVariantService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminSizeVariantServiceImpl implements AdminSizeVariantService {

    private final SizeVariantRepository sizeVariantRepository;
    private final SizeVariantMapper sizeVariantMapper;

    @Override
    public AdminSizeVariantRes getOneSizeVariant(UUID sizeVariantId) {
        SizeVariant sizeVariant = findSizeVariantFromDB(sizeVariantId);
        return sizeVariantMapper.toAdminSizeVariantRes(sizeVariant);
    }

    @Override
    public AdminSizeVariantRes updateSizeVariant(UUID sizeVariantId, AdminSizeVariantReq sizeVariantReq) {
        SizeVariant sizeVariantFromDB = findSizeVariantFromDB(sizeVariantId);
        sizeVariantMapper.updateSizeVariantFromSizeVariantReq(sizeVariantFromDB, sizeVariantReq);
        return sizeVariantMapper.toAdminSizeVariantRes(sizeVariantFromDB);
    }

    private SizeVariant findSizeVariantFromDB(UUID sizeVariantId) {
        return sizeVariantRepository.findById(sizeVariantId).orElseThrow(
                () -> new ResourceNotFoundException("SizeVariant not found with ID: " + sizeVariantId)
        );
    }
}
