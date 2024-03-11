package com.lv.finance.dtos.wallet;

import com.lv.finance.dtos.PageResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletPageResponse<T> extends PageResponse<T> {
    private WalletDto walletDto;

    public WalletPageResponse(WalletDto walletDto, List<T> content, int pageNo, int pageSize, Long totalElements, int totalPages, boolean last) {
        super(content, pageNo, pageSize, totalElements, totalPages, last);
        this.walletDto = walletDto;
    }
}
