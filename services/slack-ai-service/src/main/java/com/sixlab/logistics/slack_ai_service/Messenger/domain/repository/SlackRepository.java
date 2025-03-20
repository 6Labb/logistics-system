package com.sixlab.logistics.slack_ai_service.Messenger.domain.repository;

import com.sixlab.logistics.slack_ai_service.Messenger.domain.entity.Slack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SlackRepository extends JpaRepository<Slack, UUID> ,SlackRepositoryCustom{
}
