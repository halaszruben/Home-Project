package com.home.project.AssignmentSubmissionDemoApp.service;

import com.home.project.AssignmentSubmissionDemoApp.entity.Assignment;
import com.home.project.AssignmentSubmissionDemoApp.entity.User;
import com.home.project.AssignmentSubmissionDemoApp.enums.AssignmentStatusEnum;
import com.home.project.AssignmentSubmissionDemoApp.enums.AuthorityEnum;
import com.home.project.AssignmentSubmissionDemoApp.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepo;

    public Assignment save(User user) {
        Assignment assignment = new Assignment();
        assignment.setStatus(AssignmentStatusEnum.PENDING_SUBMISSION.getStatus());
        assignment.setNumber(findNextAssignmetnToSubmit(user));
        assignment.setUser(user);

        return assignmentRepo.save(assignment);
    }

    private Integer findNextAssignmetnToSubmit(User user) {
        Set<Assignment> assignmentsByUser = assignmentRepo.findByUser(user);
        if (assignmentsByUser == null) {
            return 1;
        } else {
            Optional<Integer> nextAssignmentNumberOpt = assignmentsByUser.stream()
                    .sorted((a1, a2) -> {
                        if (a1.getNumber() == null)
                            return 1;
                        if (a2.getNumber() == null)
                            return 1;
                        return (a2.getNumber().compareTo(a1.getNumber()));
                    })
                    .map(assignment -> {
                        if (assignment.getNumber() == null)
                            return 1;
                        return assignment.getNumber() + 1;
                    })
                    .findFirst();

            return nextAssignmentNumberOpt.orElse(1);
        }
    }

    public Set<Assignment> findByUser(User user) {
        boolean hasCodeReviewerRole = user.getAuthorities()
                .stream()
                //TODO might not work
                .filter(auth -> AuthorityEnum.ROLE_CODE_REVIEWER.name().equals(auth.getAuthority()))
                .count() > 0;

        if (hasCodeReviewerRole) {
            return assignmentRepo.findByCodeReviewer(user);
        } else
            return assignmentRepo.findByUser(user);
    }

    public Optional<Assignment> findById(Long assignmentId) {
        return assignmentRepo.findById(assignmentId);
    }

    public Assignment save(Assignment assignment) {
        return assignmentRepo.save(assignment);
    }
}
