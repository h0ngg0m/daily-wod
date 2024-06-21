package hong.dailywod.global.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Builder
public class Pagination {

    private int page;
    private List<Boolean> sortDesc;
    private List<String> sortBy;
    private Integer itemsPerPage;

    private Sort getSort() {
        final List<Order> orders = new ArrayList<>();
        for (int i = 0; i < this.sortBy.size(); i++) {
            orders.add(
                    this.sortDesc.get(i)
                            ? Order.desc(this.sortBy.get(i))
                            : Order.asc(this.sortBy.get(i)));
        }
        return Sort.by(orders);
    }

    public PageRequest getPageRequest() {
        return PageRequest.of(this.getPage() - 1, this.getItemsPerPage(), this.getSort());
    }

    public static Pagination of(int page, boolean sortDesc, String sortBy, Integer itemsPerPage) {
        return Pagination.builder()
                .page(page)
                .sortDesc(List.of(sortDesc))
                .sortBy(List.of(sortBy))
                .itemsPerPage(itemsPerPage)
                .build();
    }
}
