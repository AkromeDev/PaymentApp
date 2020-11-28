package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class MyBuddyAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyBuddyAccount.class);
        MyBuddyAccount myBuddyAccount1 = new MyBuddyAccount();
        myBuddyAccount1.setId(1L);
        MyBuddyAccount myBuddyAccount2 = new MyBuddyAccount();
        myBuddyAccount2.setId(myBuddyAccount1.getId());
        assertThat(myBuddyAccount1).isEqualTo(myBuddyAccount2);
        myBuddyAccount2.setId(2L);
        assertThat(myBuddyAccount1).isNotEqualTo(myBuddyAccount2);
        myBuddyAccount1.setId(null);
        assertThat(myBuddyAccount1).isNotEqualTo(myBuddyAccount2);
    }
}
