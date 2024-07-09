package com.tuempresa.liquorlinx.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

@Entity
@View(members = "id, fecha, cliente, total; pedidoProductos { pedidoProductos }")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private double total;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @ListProperties("id, producto.nombre, cantidad")
    private List<PedidoProducto> pedidoProductos;

    // Custom Methods
    public void crearPedido(Cliente cliente, List<Producto> productos, List<Integer> cantidades) {
        this.fecha = new Date();
        this.cliente = cliente;
        this.total = 0;
        this.pedidoProductos = new ArrayList<>();

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            int cantidad = cantidades.get(i);

            PedidoProducto pedidoProducto = new PedidoProducto();
            pedidoProducto.setPedido(this);
            pedidoProducto.setProducto(producto);
            pedidoProducto.setCantidad(cantidad);

            this.pedidoProductos.add(pedidoProducto);
            this.total += producto.getPrecio() * cantidad;
        }
    }

    public void editarPedido(List<Producto> productos, List<Integer> cantidades) {
        this.total = 0;
        this.pedidoProductos.clear();

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            int cantidad = cantidades.get(i);

            PedidoProducto pedidoProducto = new PedidoProducto();
            pedidoProducto.setPedido(this);
            pedidoProducto.setProducto(producto);
            pedidoProducto.setCantidad(cantidad);

            this.pedidoProductos.add(pedidoProducto);
            this.total += producto.getPrecio() * cantidad;
        }
    }

    public void eliminarPedido(EntityManager entityManager) {
        for (PedidoProducto pedidoProducto : pedidoProductos) {
            entityManager.remove(pedidoProducto);
        }
        entityManager.remove(this);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<PedidoProducto> getPedidoProductos() {
        return pedidoProductos;
    }

    public void setPedidoProductos(List<PedidoProducto> pedidoProductos) {
        this.pedidoProductos = pedidoProductos;
    }
}