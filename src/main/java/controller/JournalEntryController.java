package controller;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.JournalEntryDTO;
import entity.JournalEntry;
import services.JournalEntryServices;

@RestController
@RequestMapping("/Journal")
public class JournalEntryController {
	
	private final JournalEntryServices journalEntryServices;
	
	public JournalEntryController(JournalEntryServices journalEntryServices) {
		this.journalEntryServices = journalEntryServices;
	}
	
	@GetMapping("GetJournalEntry")
	public ResponseEntity<?> findJournalEntries() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		List<JournalEntry> journalEntries = journalEntryServices.getJournalEntriesByName(userName);
		if(!journalEntries.isEmpty()) {
			return new ResponseEntity<>(journalEntries,HttpStatus.FOUND);
		}
		return new ResponseEntity<>(journalEntries,HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("CreateEntry")
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntryDTO myEntryDTO){
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			JournalEntry myentry = journalEntryServices.saveEntry(myEntryDTO,userName);
			return new ResponseEntity<>(myentry, HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("DeleteById/{myId}")
	public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		journalEntryServices.deleteById(myId,userName);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("UpdateJournalEntry/{myId}")
	public ResponseEntity<?> updateEntry(
			@PathVariable ObjectId myId,
			@RequestBody JournalEntry newEntry
			) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		JournalEntry journalEntries = journalEntryServices
											.getJournalEntriesByName(userName)
											.stream()
											.filter(x->x.getId().equals(myId))
											.findFirst()
											.orElse(null);
		if(journalEntries != null) {
			Optional<JournalEntry> journalEntry = journalEntryServices.findById(myId);
			if(journalEntry.isPresent()) {
				JournalEntry old = journalEntry.get();
				old.setTitle(newEntry.getTitle());
				old.setContent(newEntry.getContent());
				journalEntryServices.saveEntry(old);
				return new ResponseEntity<>(journalEntries,HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<>(journalEntries,HttpStatus.EXPECTATION_FAILED);
	}
	
	@GetMapping("GetJournalEntryById/{myId}")
	public ResponseEntity<JournalEntry> getEntry(@PathVariable ObjectId myId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		List<JournalEntry> journalEntries = journalEntryServices.getJournalEntriesByName(userName).stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
		if(!journalEntries.isEmpty()) {
			Optional<JournalEntry> journalEntry = journalEntryServices.findById(myId);
			if(journalEntry.isPresent()) {
				return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
