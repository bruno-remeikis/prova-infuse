package com.remeikis.provainfuse.controller;

import com.remeikis.provainfuse.exceptions.BusinessException;
import com.remeikis.provainfuse.model.Pedido;
import com.remeikis.provainfuse.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/pedido", consumes = { /*MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, */MediaType.ALL_VALUE }) //(value = "/pedido", consumes = { "application/json", "application/xml" })
public class PedidoController
{
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity findAll(@RequestParam(name = "cliente", required = false) Integer codigoCliente) {
        return ResponseEntity.ok(
            codigoCliente == null
                ? pedidoService.findAll()
                : pedidoService.findByCodigoCliente(codigoCliente)
        );
    }

    @GetMapping("/filtro")
    public ResponseEntity filter(
        @RequestParam(required = false) Integer numeroControle,
        @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataCadastro/*,
        @RequestParam(required = false) @DateTimeFormat(fallbackPatterns = { "dd-MM-yyyy", "dd-MM-yyyy HH:mm" }) Date de,
        @RequestParam(required = false) @DateTimeFormat(fallbackPatterns = { "dd-MM-yyyy", "dd-MM-yyyy HH:mm" }) Date ate*/
    ) {
        List<Pedido> pedidos;

        if (numeroControle != null)
            return ResponseEntity.ok(pedidoService.findById(numeroControle));

        if(dataCadastro != null)
            return ResponseEntity.ok(pedidoService.findByDataCadastro(dataCadastro));

        //if(de != null || ate != null)
        //    return ResponseEntity.ok(pedidoService.findByIntervaloDataCadastro(de, ate));

        return ResponseEntity.ok(pedidoService.findAll());
    }

    @PostMapping
    public ResponseEntity save(@RequestBody List<Pedido> pedidos) {
        try {
            return ResponseEntity.ok(
                pedidoService.save(pedidos)
            );
        }
        catch(BusinessException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }
}
