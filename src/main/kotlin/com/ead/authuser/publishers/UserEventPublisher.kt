package com.ead.authuser.publishers

import com.ead.authuser.dtos.UserEventDto
import com.ead.authuser.enums.ActionType
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class UserEventPublisher(private val rabbitTemplate: RabbitTemplate) {

    @Value(value = "\${ead.broker.exchange.userEvent}")
    private val exchangeUserEvent: String? = null

    fun publishUserEvent(userEventDto: UserEventDto, actionType: ActionType) {
        userEventDto.actionType = actionType.toString()

        rabbitTemplate.convertAndSend(exchangeUserEvent!!, "", userEventDto)
    }
}