package com.bookings.appreservas.controller;

import com.bookings.appreservas.entities.Customer;
import com.bookings.appreservas.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@Api(tags="Customer", value = "Servicio Web RESTFul de Customers")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Listar Customers", notes="Método para listar todos los customers")
    @ApiResponses({
            @ApiResponse(code=201, message = "Customers encontrados"),
            @ApiResponse(code=404, message = "Customers no encontrados")
    })
    public ResponseEntity<List<Customer>>findAll(){
        try{
            List<Customer> customers = customerService.getAll();
            if(customers.size()>0)
                return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
            else
                return new ResponseEntity<List<Customer>>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<List<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Customer por Id", notes = "Método para encontrar un customer por su respectivo Id")
    @ApiResponses({
            @ApiResponse(code=201, message = "Customer encontrado"),
            @ApiResponse(code=404, message = "Customer no encontrado")
    })
    public ResponseEntity<Customer> findById(@PathVariable("id") Long id){
        try{
            Optional<Customer> customer = customerService.getById(id);
            if(!customer.isPresent())
                return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchByDni/{dni}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Customer por DNI", notes = "Método para encontrar un customer por su respectivo DNI")
    @ApiResponses({
            @ApiResponse(code=201, message = "Customer encontrado"),
            @ApiResponse(code=404, message = "Customer no encontrado")
    })
    public ResponseEntity<Customer> findByDni(@PathVariable("dni") String dni){
        try{
            Customer customer = customerService.findByDni(dni);
            if(customer==null)
                return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Customer>(customer,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="searchByLastname/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Customers por lastname", notes = "Método para encontrar customers por su respectivo lastname")
    @ApiResponses({
            @ApiResponse(code=201, message = "Customers encontrados"),
            @ApiResponse(code=404, message = "Customers no encontrados")
    })
    public ResponseEntity<List<Customer>> findByLastName(@PathVariable("lastname") String lastname){
        try{
            List<Customer> customers = customerService.findByLastName(lastname);
            if(customers.size()>0)
                return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
            else
                return new ResponseEntity<List<Customer>>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<List<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="searchByFirstname/{firstname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Customers por firstname", notes = "Método para encontrar customers por su respectivo firstname")
    @ApiResponses({
            @ApiResponse(code=201, message = "Customers encontrados"),
            @ApiResponse(code=404, message = "Customers no encontrados")
    })
    public ResponseEntity<List<Customer>> findByFirstName(@PathVariable("firstname") String firstname){
        try{
            List<Customer> customers = customerService.findByFirstName(firstname);
            if(customers.size()>0)
                return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
            else
                return new ResponseEntity<List<Customer>>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<List<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="searchByFirstnameAndLastname/{firstname}/{lastname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Buscar Customers por firstname y lastname", notes = "Método para encontrar customers por su respectivos firstname y lastname")
    @ApiResponses({
            @ApiResponse(code=201, message = "Customers encontrados"),
            @ApiResponse(code=404, message = "Customers no encontrados")
    })
    public ResponseEntity<List<Customer>> findByFirstnameAndLastname(
            @PathVariable("firstname") String firstname, @PathVariable("lastname") String lastname){
        try{
            List<Customer> customers = customerService.findByFirstNameAndLastName(firstname,lastname);
            if(customers.size()>0)
                return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
            else
                return new ResponseEntity<List<Customer>>(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            return new ResponseEntity<List<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registro de Customers", notes ="Método que registra customers en BD" )
    @ApiResponses({
            @ApiResponse(code=201, message = "Customer creado"),
            @ApiResponse(code=404, message = "Customer no creado")
    })
    public ResponseEntity<Customer> insertCustomer(@Valid @RequestBody Customer customer){
        try{
            Customer customerNew = customerService.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(customerNew);
        }catch (Exception e){
            return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Actualización de datos de Customers", notes = "Método que actualizar los datos de Customers")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de Customer actualizados"),
            @ApiResponse(code=404, message = "Customer no encontrado")
    })
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable("id") Long id, @Valid @RequestBody Customer customer) {
        try{
            Optional<Customer> customerUp = customerService.getById(id);
            if(!customerUp.isPresent())
                return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
            customer.setId(id);
            customerService.save(customer);
            return new ResponseEntity<Customer>(customer, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Eliminación de Customers", notes = "Método que elimina los datos de Customers")
    @ApiResponses({
            @ApiResponse(code=200, message = "Datos de Customer eliminados"),
            @ApiResponse(code=404, message = "Customer no encontrado")
    })
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id")Long id){
        try{
            Optional<Customer> customerDelete = customerService.getById(id);
            if(!customerDelete.isPresent())
                return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);

            customerService.delete(id);
            return new ResponseEntity<Customer>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
   }
}
