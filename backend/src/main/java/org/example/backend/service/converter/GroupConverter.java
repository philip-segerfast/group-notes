package org.example.backend.service.converter;

import org.example.backend.api.modules.groups.GroupRequest;
import org.example.backend.entity.UserGroup;
import org.springframework.stereotype.Component;

@Component
public class GroupConverter {
    public UserGroup convert(GroupRequest groupRequest) {
        return new UserGroup(groupRequest.name(), groupRequest.userId());
    }
}
