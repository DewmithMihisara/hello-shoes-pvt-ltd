package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.BranchDTO;
import lk.ijse.helloshoebackend.entity.BranchEntity;
import lk.ijse.helloshoebackend.enums.Id;
import lk.ijse.helloshoebackend.repository.BranchRepository;
import lk.ijse.helloshoebackend.service.BranchService;
import lk.ijse.helloshoebackend.util.IdService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dewmith Mihisara
 * @date 2024-04-24
 * @since 0.0.1
 */
@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepo;

    private final ModelMapper mapper;

    public BranchServiceImpl(BranchRepository branchRepo, ModelMapper mapper) {
        this.branchRepo = branchRepo;
        this.mapper = mapper;
    }


    @Override
    public boolean saveBranch(BranchDTO branchDTO) {
        branchDTO.setBranchId(IdService.generateID(Id.BRANCH_ID));
        BranchEntity save = branchRepo.save(mapper.map(branchDTO, BranchEntity.class));
        return save != null;
    }

    @Override
    public List<BranchDTO> getAllBranches() {
        return branchRepo.findAll().stream().map(branch -> mapper.map(branch, BranchDTO.class)).toList();
    }

    @Override
    public boolean updateBranch(BranchDTO branchDTO) {
        BranchEntity branch = branchRepo.findById(branchDTO.getBranchId()).orElse(null);
        if (branch != null) {
            branch.setBranchName(branchDTO.getBranchName());
            branch.setBranchContact(branchDTO.getBranchContact());
            branch.setAddress(branchDTO.getAddress());
            BranchEntity save = branchRepo.save(branch);
            return save != null;
        }
        return false;
    }

    @Override
    public boolean deleteBranch(String branchId) {
        branchRepo.deleteById(branchId);
        return true;
    }

    @Override
    public List<String> getBranchIds() {
        return branchRepo.findAll().stream().map(BranchEntity::getBranchId).toList();
    }

    @Override
    public BranchEntity getBranchById(String branchId) {
        return branchRepo.findById(branchId).orElse(null);
    }
}
