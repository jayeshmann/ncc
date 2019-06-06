package com.tsa.NCC_dte_punjab.json_parsing;

import java.util.Map;

public interface VolleyRequestHandlerIF {
    public String getApiResponse(int flag);
    public void setApiResponse(Map response);
}
