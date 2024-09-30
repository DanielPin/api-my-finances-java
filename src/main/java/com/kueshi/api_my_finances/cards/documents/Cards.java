package com.kueshi.api_my_finances.cards.documents;

import com.kueshi.api_my_finances.cards.dto.CardCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cards {

    @Id
    private String  id;

    @Indexed(unique = true)
    private String name;

    private String icon;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Cards (CardCreateDTO card){
        this.name = card.name();
        this.icon = card.icon();
    }


}
