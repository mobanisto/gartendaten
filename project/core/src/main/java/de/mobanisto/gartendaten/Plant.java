package de.mobanisto.gartendaten;

import lombok.Getter;
import lombok.Setter;

public class Plant extends Thing
{

	/**
	 * Gibt an, ob eine Kultur in Listen auftauchen soll. Das Gegenteil trifft
	 * zu z.B. auf Obstbäume, die erfasst sind aber nur als Mischkulturpartner
	 */
	@Getter
	@Setter
	private boolean primary;

	public Plant(String name, boolean primary)
	{
		super(name);
		this.primary = primary;
	}

}
