package org.esmerilprogramming.tecnolity.pedidos

 class RegistroHistoricoStatusPedido
{
	private String status
	private String data
	
	 RegistroHistoricoStatusPedido(String status, String data)
	{
		setStatus(status)
		setData(data)
	}
	
	 void setStatus(String status)
	{
		this.status = status
	}
	
	 void setData(String data)
	{
		this.data = data
	}
	
	 String getStatus()
	{
		return this.status
	}
	
	 String getData()
	{
		return this.data
	}
}
