package telran.library.dto;

import java.util.*;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class BookDto {
	private long isbn;
	private String title;
	private int amount;
	private List<String> authorNames;
	private Cover cover;
	private int pickPeriod;

}
