package ir.co.isc.assignment.cardholder.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageDto<T> {
    private List<T> content;
    private Integer page;
    private Integer size;
    private Long total;
}
