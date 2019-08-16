package kristian9577.jsonexercises.service.impl;

import kristian9577.jsonexercises.domain.dto.UserSeedDto;
import kristian9577.jsonexercises.domain.entity.User;
import kristian9577.jsonexercises.repository.UserRepo;
import kristian9577.jsonexercises.service.UserService;
import kristian9577.jsonexercises.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(ValidatorUtil validatorUtil, ModelMapper modelMapper, UserRepo userRepo) {
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
    }


    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {
        for (UserSeedDto userSeedDto : userSeedDtos) {
            if (!validatorUtil.isValid(userSeedDto)) {
                validatorUtil.violations(userSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));
                continue;
            }
            User user = this.modelMapper.map(userSeedDto,User.class);
            this.userRepo.saveAndFlush(user);
        }
    }
}
