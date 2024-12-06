package com.example.toolsleasing.model;

public class CReport2Item extends CFruit
{
    public CReport2Item(String country, Long fruitsCount)
    {
        super(country);
        setFruitsCount(fruitsCount);
    }
    private Long fruitsCount;

    public Long getFruitsCount() {
        return fruitsCount;
    }

    public void setFruitsCount(Long fruitsCount) {
        if (fruitsCount>=0)
            this.fruitsCount = fruitsCount;
    }
}
