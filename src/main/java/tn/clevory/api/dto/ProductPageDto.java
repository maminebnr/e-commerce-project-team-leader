package tn.clevory.api.dto;

import java.util.List;

/**
 * Envelope de pagination simple (J1).
 * En J2 on utilisera directement Page&lt;ProductDto&gt; ou PagedModel.
 */
public class ProductPageDto {

    private List<ProductDto> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public ProductPageDto() {}

    public ProductPageDto(List<ProductDto> content, int page, int size,
                          long totalElements, int totalPages) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<ProductDto> getContent() { return content; }
    public void setContent(List<ProductDto> content) { this.content = content; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
}
