package guru.bonacci.commsai.domain;

import java.util.UUID;

public record DeliverableMessage(
 UUID uuid,
 String message
) {}
