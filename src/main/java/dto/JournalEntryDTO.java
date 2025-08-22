package dto;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JournalEntryDTO {
	
	private ObjectId id;
	private String title;
	private String content;
	private LocalDateTime date;
	

}
