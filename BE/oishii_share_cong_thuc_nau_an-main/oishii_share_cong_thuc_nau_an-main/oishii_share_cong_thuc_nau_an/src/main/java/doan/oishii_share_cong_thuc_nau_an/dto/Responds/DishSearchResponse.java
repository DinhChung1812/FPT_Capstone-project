package doan.oishii_share_cong_thuc_nau_an.dto.Responds;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishSearchResponse {
    private List<DishResponse> dishResponseList;
    private Integer numOfPages;
    private Integer pageIndex;
}
