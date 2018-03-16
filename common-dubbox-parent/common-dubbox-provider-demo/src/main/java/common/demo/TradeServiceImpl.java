package common.demo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * rest服务
 *
 * @author zhangjj
 * @create 2018-01-04 11:57
 **/
@Path("trade")
public class TradeServiceImpl implements ITradeService {

    @Path("trade")
    @GET
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    @Override
    public String trade(float userName) {

        return "trade success";
    }
}
