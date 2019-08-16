package kristian9577.jsonexercises.service;

import kristian9577.jsonexercises.domain.dto.UserSeedDto;

public interface UserService {

    void seedUsers(UserSeedDto[] userSeedDtos);
}
