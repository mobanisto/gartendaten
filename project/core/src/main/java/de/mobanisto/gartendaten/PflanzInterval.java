package de.mobanisto.gartendaten;

import java.time.MonthDay;

import lombok.Getter;

public class PflanzInterval
{

	@Getter
	private MonthDay start;
	@Getter
	private MonthDay end;

	public PflanzInterval(MonthDay start, MonthDay end)
	{
		this.start = start;
		this.end = end;
	}

}
