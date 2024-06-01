package lk.ijse.helloshoebackend.service.impl;

import lk.ijse.helloshoebackend.dto.BranchDTO;
import lk.ijse.helloshoebackend.entity.BranchEntity;
import lk.ijse.helloshoebackend.enums.Id;
import lk.ijse.helloshoebackend.repository.BranchRepository;
import lk.ijse.helloshoebackend.service.BranchService;
import lk.ijse.helloshoebackend.util.IdService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    private static final Logger logger = LoggerFactory.getLogger(BranchServiceImpl.class);

    private final BranchRepository branchRepository;
    private final ModelMapper mapper;

    public BranchServiceImpl(BranchRepository branchRepository, ModelMapper mapper) {
        this.branchRepository = branchRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean saveBranch(BranchDTO branchDTO) {
        branchDTO.setBranchId(IdService.generateID(Id.BRANCH_ID));
        logger.info("Generated BranchEntity ID: {}", branchDTO.getBranchId());

        BranchEntity branchEntity = mapper.map(branchDTO, BranchEntity.class);
        BranchEntity savedBranchEntity = branchRepository.save(branchEntity);
        boolean isSaved = savedBranchEntity != null;
        logger.info("BranchEntity Saved: {}", isSaved);

        return isSaved;
    }

    @Override
    public List<BranchDTO> getAllBranches() {
        logger.info("Fetching all branches");
        List<BranchDTO> branches = branchRepository.findAll().stream()
                .map(branch -> mapper.map(branch, BranchDTO.class))
                .toList();
        logger.info("Total branches fetched: {}", branches.size());

        return branches;
    }

    @Override
    public boolean updateBranch(BranchDTO branchDTO) {
        logger.info("Updating BranchEntity ID: {}", branchDTO.getBranchId());

        BranchEntity branchEntity = branchRepository.findById(branchDTO.getBranchId()).orElse(null);
        if (branchEntity != null) {
            logger.info("BranchEntity found for ID: {}", branchDTO.getBranchId());
            branchEntity.setBranchName(branchDTO.getBranchName());
            branchEntity.setBranchContact(branchDTO.getBranchContact());
            branchEntity.setAddress(branchDTO.getAddress());

            BranchEntity savedBranchEntity = branchRepository.save(branchEntity);
            boolean isUpdated = savedBranchEntity != null;
            logger.info("BranchEntity Updated: {}", isUpdated);

            return isUpdated;
        } else {
            logger.warn("BranchEntity not found for ID: {}", branchDTO.getBranchId());
            return false;
        }
    }

    @Override
    public boolean deleteBranch(String branchId) {
        logger.info("Deleting BranchEntity ID: {}", branchId);

        branchRepository.deleteById(branchId);
        boolean isDeleted = !branchRepository.existsById(branchId);
        logger.info("BranchEntity Deleted: {}", isDeleted);

        return isDeleted;
    }

    @Override
    public List<String> getBranchIds() {
        logger.info("Fetching all branch IDs");
        List<String> branchIds = branchRepository.findAll().stream()
                .map(BranchEntity::getBranchId)
                .toList();
        logger.info("Total branch IDs fetched: {}", branchIds.size());

        return branchIds;
    }

    @Override
    public BranchEntity getBranchById(String branchId) {
        logger.info("Fetching BranchEntity for ID: {}", branchId);

        BranchEntity branchEntity = branchRepository.findById(branchId).orElse(null);
        if (branchEntity != null) {
            logger.info("BranchEntity found for ID: {}", branchId);
        } else {
            logger.warn("BranchEntity not found for ID: {}", branchId);
        }

        return branchEntity;
    }
}