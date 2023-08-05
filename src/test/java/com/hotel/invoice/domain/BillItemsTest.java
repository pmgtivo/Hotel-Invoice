package com.hotel.invoice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hotel.invoice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BillItemsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillItems.class);
        BillItems billItems1 = new BillItems();
        billItems1.setId(1L);
        BillItems billItems2 = new BillItems();
        billItems2.setId(billItems1.getId());
        assertThat(billItems1).isEqualTo(billItems2);
        billItems2.setId(2L);
        assertThat(billItems1).isNotEqualTo(billItems2);
        billItems1.setId(null);
        assertThat(billItems1).isNotEqualTo(billItems2);
    }
}
