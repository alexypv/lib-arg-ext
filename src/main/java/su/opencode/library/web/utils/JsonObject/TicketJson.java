package su.opencode.library.web.utils.JsonObject;

import su.opencode.library.web.model.entities.TicketEntity;

public class TicketJson {

    public TicketJson() {
    }

    public TicketJson(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TicketJson convertTicketEntityToTicketJson(TicketEntity ticketEntity){
        return new TicketJson(ticketEntity.getCode());
    }
}
