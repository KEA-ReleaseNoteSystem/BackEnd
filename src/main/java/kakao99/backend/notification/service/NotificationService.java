package kakao99.backend.notification.service;

import kakao99.backend.entity.Issue;
import kakao99.backend.entity.Member;
import kakao99.backend.entity.Notification;
import kakao99.backend.entity.Project;
import kakao99.backend.entity.types.NotificationType;
import kakao99.backend.issue.dto.DragNDropDTO;
import kakao99.backend.notification.dto.NotificationDTO;
import kakao99.backend.notification.rabbitmq.dto.RequestMessageDTO;
import kakao99.backend.notification.repository.NotificationRepository;
import kakao99.backend.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ProjectRepository projectRepository;

    public void createNotification(RequestMessageDTO requestMessageDTO) {
        Long projectId = requestMessageDTO.getProjectId();
        Project project = projectRepository.findProjectById(projectId);
        System.out.println("project.getId() = " + project.getId());

//        if (optionalProject.isEmpty()) {
//            throw new NoSuchElementException(projectId + "프로젝트가 없습니다.");
//        }
//        Project project = optionalProject.get();
        log.info("noti 저장 시도");
        if (requestMessageDTO.getType().equals(NotificationType.ISSUEDONE)) {
            log.info("noti 저장 중");
            Notification newNotification = new Notification().builder()
                    .message("이슈가 해결되었습니다.")
                    .type(requestMessageDTO.getType().getType())
                    .typeSpecificId(requestMessageDTO.getSpecificTypeId())
                    .project(project).build();
            notificationRepository.save(newNotification);

        }

//        notificationRepository.createNotification(requestMessageDTO);
    }

    public List<NotificationDTO> getAllNotifications(Long projectId) {
        List<Notification> allNotificationOfProject = notificationRepository.findAllByProjectId(projectId);

        List<NotificationDTO> notificationDTOS = NotificationDTO.AllNotificationOfProject(allNotificationOfProject);

        return notificationDTOS;
    }
}
