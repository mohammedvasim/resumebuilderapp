package in.vasim.resumebuilderapi.document;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    
    private String id;
    private String name;
    private String email;
    private String password;
    private String profileImageUrl;
    @Builder.Default
    private String subscriptionPlan="basic";
    @Builder.Default
    private String emailVerified="false";
    private String verificationToken;
    private LocalDateTime verificationExpires;

    @CreatedDate
    @Column(name="created_date", updatable=false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate   
    @Column(name="updated_date")
    private LocalDateTime updatedAt;

}
