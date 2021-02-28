package com.finalproject.shelter.service;

import com.finalproject.shelter.ifs.Crudinterface;
import com.finalproject.shelter.model.Header;
import com.finalproject.shelter.model.entity.AdminUser;
import com.finalproject.shelter.model.network.request.AdminUserApiRequest;
import com.finalproject.shelter.model.network.response.AdminUserApiResponse;
import com.finalproject.shelter.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminUserApiLogicService implements Crudinterface<AdminUserApiRequest, AdminUserApiResponse>{

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public Header<AdminUserApiResponse> create(Header<AdminUserApiRequest> request) {

        AdminUserApiRequest body = request.getData();

        AdminUser adminUser = AdminUser.builder()
                .userId(body.getUserId())
                .password(body.getPassword())
                .name(body.getName())
                .build();

        AdminUser newadminUser = adminUserRepository.save(adminUser);

        return response(newadminUser);
    }

    @Override
    public Header<AdminUserApiResponse> read(Long id) {
        return adminUserRepository.findById(id)
                .map(this::response)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<AdminUserApiResponse> update(Header<AdminUserApiRequest> request) {

        AdminUserApiRequest body = request.getData();

        return adminUserRepository.findById(body.getId())
                .map(adminUser -> {
                    adminUser
                            .setUserId(body.getUserId())
                            .setPassword(body.getPassword())
                            .setName(body.getName());
                    return adminUser;
                })
                .map(adminUser -> adminUserRepository.save(adminUser))
                .map(this::response)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return adminUserRepository.findById(id)
                .map(adminUser -> {
                    adminUserRepository.delete(adminUser);
                    return Header.OK();
                })
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    public Header<AdminUserApiResponse> response(AdminUser adminUser){

        AdminUserApiResponse body = AdminUserApiResponse.builder()
                .id(adminUser.getId())
                .userId(adminUser.getUserId())
                .password(adminUser.getPassword())
                .name(adminUser.getName())
                .createdAt(adminUser.getCreatedAt())
                .uncreatedAt(adminUser.getUncreatedAt())
                .updatedAt(adminUser.getUpdatedAt())
                .updatedBy(adminUser.getUpdatedBy())
                .build();

        return Header.OK(body);
    }
}
