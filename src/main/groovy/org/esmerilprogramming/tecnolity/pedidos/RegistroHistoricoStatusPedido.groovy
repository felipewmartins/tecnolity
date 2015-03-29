package org.esmerilprogramming.tecnolity.pedidos

public class RegistroHistoricoStatusPedido
{
	private String status
	private String data
	
	public RegistroHistoricoStatusPedido(String status, String data)
	{
		setStatus(status)
		setData(data)
	}
	
	public void setStatus(String status)
	{
		this.status = status
	}
	
	public void setData(String data)
	{
		this.data = data
	}
	
	public String getStatus()
	{
		return this.status
	}
	
	public String getData()
	{
		return this.data
	}
}
