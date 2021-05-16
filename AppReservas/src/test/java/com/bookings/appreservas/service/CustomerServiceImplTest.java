package com.bookings.appreservas.service;

import com.bookings.appreservas.entities.Customer;
import com.bookings.appreservas.repository.ICustomerRepository;
import com.bookings.appreservas.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @Mock// simularemos el comportamiento de ICustomerRepository
    private ICustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void saveTest(){ // podemos reutilizarlo para ACTUALIZAR Y CREAR
        // se escriben todos los metodos del mismo
        Customer customer = new Customer(1L,"Juan","Perez",
                "12345678","Av. No se 123","987654321","juan.perez@upc.edu.pe");

        given(customerRepository.save(customer)).willReturn(customer);

        Customer savedCustomer = null;
        try{
            savedCustomer = customerService.save(customer);
        }catch (Exception e){
        }
        assertThat(savedCustomer).isNotNull();
        verify(customerRepository).save(any(Customer.class));
    }
    @Test
    void findByIdTest() throws Exception {
        Long id = 1L;
        Customer customer = new Customer(1L,"Juan","Perez",
                "12345678","Av. No se 123","987654321","juan.perez@upc.edu.pe");

        given(customerRepository.findById(id)).willReturn(Optional.of(customer));

        Optional<Customer> expected = customerService.getById(id);
        assertThat(expected).isNotNull();
    }
     // obtener la lista de todos los objetos
    @Test
    void findAllTest() throws Exception {
        List<Customer> list = new ArrayList<>();
        list.add(new Customer(1L,"Juan","Perez",
                "12345678","Av. No se 123","987654321","juan.perez@upc.edu.pe"));
        list.add(new Customer(2L,"Juan","Perez",
                "12345678","Av. No se 123","987654321","juan.perez@upc.edu.pe"));
        list.add(new Customer(3L,"Juan","Perez",
                "12345678","Av. No se 123","987654321","juan.perez@upc.edu.pe"));
        list.add(new Customer(4L,"Juan","Perez",
                "12345678","Av. No se 123","987654321","juan.perez@upc.edu.pe"));

        given(customerRepository.findAll()).willReturn(list);
        List<Customer> expected = customerService.getAll();
        assertEquals(expected, list);
    }

    @Test
    void deleteTest() throws Exception {
        Long id = 1L;
        customerService.delete(id);
        verify(customerRepository, times(1)).deleteById(id);
    }
}
