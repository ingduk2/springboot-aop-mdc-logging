package com.logging.aopmdc.common.filter.txid;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TxIdUUIDGenerator implements TxIdGenerator{
    @Override
    public String generateTxId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
