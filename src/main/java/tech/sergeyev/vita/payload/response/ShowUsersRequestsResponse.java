package tech.sergeyev.vita.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tech.sergeyev.vita.persistence.model.requests.Request;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ShowUsersRequestsResponse {
    List<Request> usersRequests;
    List<Request> submittedRequests;
}
