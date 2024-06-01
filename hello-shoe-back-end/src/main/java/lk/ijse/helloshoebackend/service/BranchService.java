package lk.ijse.helloshoebackend.service;

import lk.ijse.helloshoebackend.dto.BranchDTO;
import lk.ijse.helloshoebackend.entity.BranchEntity;

import java.util.List;
/**
 * @author Dewmith Mihisara
 * @date 2024-04-23
 * @since 0.0.1
 */
public interface BranchService {
    boolean saveBranch(BranchDTO branchDTO);

    List<BranchDTO> getAllBranches();

    boolean updateBranch(BranchDTO branchDTO);

    boolean deleteBranch(String branchId);

    List<String> getBranchIds();

    BranchEntity getBranchById(String branchId);
}
