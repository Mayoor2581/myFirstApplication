package dto;


import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import entity.JournalEntry;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserEntryDTO {
	
	private ObjectId id;
	private String userName;
	private String password;
	private List<JournalEntry> journalEntries = new ArrayList<>();
	private List<String> roles;
	
}
