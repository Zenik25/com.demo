package com.demo.restlmpl;

import com.demo.POJO.OrderBillEmail;
import com.demo.dao.OrderBillEmailDao;
//import com.demo.model.OrderBillEmail;
import com.demo.rest.OrderBillEmailRest;
import com.demo.utils.EmailUtils;
import com.demo.wrapper.OrderBillWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderBillEmailRestImpl implements OrderBillEmailRest {

    @Autowired
    private OrderBillEmailDao orderBillEmailDao;

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> sendOrderBillEmail(OrderBillWrapper wrapper) {
        OrderBillEmail bill = new OrderBillEmail();
        bill.setEmail(wrapper.getEmail());
        bill.setTableNumber(wrapper.getTableNumber());
        bill.setOrderedProductNames(wrapper.getOrderedProductNames());
        bill.setQuantity(wrapper.getQuantity());
        bill.setPrice(wrapper.getPrice());
        bill.setTotalPrice(wrapper.getTotalPrice());
        bill.setTaxAmount(wrapper.getTaxAmount());
        bill.setNetTotal(wrapper.getNetTotal());

        orderBillEmailDao.save(bill);

        StringBuilder content = new StringBuilder();
        content.append("🧾 Order Bill 🧾\n")
                .append("Table Number: ").append(bill.getTableNumber()).append("\n")
                .append("Date: ").append(bill.getDate()).append(" ").append(bill.getTime()).append("\n\n")
                .append("Items:\n");

        List<String> items = bill.getOrderedProductNames();
        List<Integer> quantities = bill.getQuantity();
        List<Integer> prices = bill.getPrice();

        for (int i = 0; i < items.size(); i++) {
            content.append(items.get(i))
                    .append(" x ").append(quantities.get(i))
                    .append(" = ").append(prices.get(i) * quantities.get(i))
                    .append(" Ks\n");
        }

        content.append("\nTax (5%): ").append(bill.getTaxAmount()).append(" Ks\n");
        content.append("Total: ").append(bill.getNetTotal()).append(" Ks\n");

        emailUtils.sendSimpleMessage(bill.getEmail(), "Your Order Bill", content.toString(), null);

        return new ResponseEntity<>("Order bill email sent successfully", HttpStatus.OK);
    }
}