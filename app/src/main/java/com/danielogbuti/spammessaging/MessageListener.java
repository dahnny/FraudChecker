package com.danielogbuti.spammessaging;

import java.util.List;

public interface MessageListener {

    void messageReceived(List<String> message);
}
