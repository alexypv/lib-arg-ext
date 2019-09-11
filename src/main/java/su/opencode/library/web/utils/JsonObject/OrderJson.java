package su.opencode.library.web.utils.JsonObject;

import su.opencode.library.web.model.entities.BookOrderEntity;

import java.util.Date;

public class OrderJson {

    public OrderJson() {
    }

    public OrderJson(int id, String code, String creatorName, Date startDate, Date endDate, Date realReturnDate) {
        this.id = id;
        this.code = code;
        this.creatorName = creatorName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.realReturnDate = realReturnDate;
    }

    private int id;
    private String code;
    private String creatorName;
    private Date startDate;
    private Date endDate;
    private Date realReturnDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getRealReturnDate() {
        return realReturnDate;
    }

    public void setRealReturnDate(Date realReturnDate) {
        this.realReturnDate = realReturnDate;
    }

    public OrderJson convertBooksOrderEntityToOrderJson(BookOrderEntity bookOrderEntity) {
        Date returnData;
        if (bookOrderEntity.getRealReturnDate() == null) {
            returnData = null;
        } else {
            returnData = bookOrderEntity.getRealReturnDate();
        }

        return new OrderJson(
                bookOrderEntity.getId(),
                bookOrderEntity.getCode(),
                bookOrderEntity.getCreatorEntity().getUsername(),
                bookOrderEntity.getStartDate(),
                bookOrderEntity.getEndDate(),
                returnData
        );

    }

}

