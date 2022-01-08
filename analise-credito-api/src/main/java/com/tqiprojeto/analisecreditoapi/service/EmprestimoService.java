package com.tqiprojeto.analisecreditoapi.service;

import com.tqiprojeto.analisecreditoapi.entity.Cliente;
import com.tqiprojeto.analisecreditoapi.entity.Emprestimo;
import com.tqiprojeto.analisecreditoapi.exception.ClienteNaoCadastradoException;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoDataPrimeiraParcelaExcedida;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoNaoCadastradoException;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoParcelasExcedida;
import com.tqiprojeto.analisecreditoapi.repository.ClienteRepository;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRepository;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRetornoDetalhado;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRetornoSimples;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {

    private EmprestimoRepository emprestimoRepository;

    private ClienteRepository clienteRepository;

    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository, ClienteRepository clienteRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.clienteRepository = clienteRepository;
    }

    //método para listar todos os emprestimos
    public List<Emprestimo> listarTodosEmprestimos (){
        return emprestimoRepository.findAll();
    }

    //método para listagem básica dos emprestimos cadastrados, vinculado a um ID de cliente
    public List<EmprestimoRetornoSimples> listarSimples(Integer id) throws EmprestimoNaoCadastradoException {

        List<EmprestimoRetornoSimples> listaSimples = emprestimoRepository.listaSimples(id);

        if (listaSimples.isEmpty()){
           throw new EmprestimoNaoCadastradoException(id);}
        else {
        return listaSimples;
        }

    }

    //método para listagem mais detalhada dos emprestimos cadastrados, vinculado a um ID de cliente
   public List<EmprestimoRetornoDetalhado> listarEmprestimoDetalhado (Integer id) throws EmprestimoNaoCadastradoException {

       List<EmprestimoRetornoDetalhado> listaDetalhada = emprestimoRepository.listarEmprestimoDetalhado(id);

        if (listaDetalhada.isEmpty()){
            throw new EmprestimoNaoCadastradoException(id);
        }else{
        return listaDetalhada;}
    }

    //método para buscar um emprestimo por ID
       public Emprestimo buscarPorId(Integer id) throws EmprestimoNaoCadastradoException {

        Emprestimo emprestimo = emprestimoRepository.findById(id).orElseThrow(() -> new EmprestimoNaoCadastradoException(id));
        return emprestimo ;
    }

    //método utilizado para solicitar um emprestimo, juntamente com as exigências mínimas das regras de negócio
    public Emprestimo solicitar(Emprestimo emprestimo) throws EmprestimoParcelasExcedida, ParseException, EmprestimoDataPrimeiraParcelaExcedida, ClienteNaoCadastradoException {

        setClienteEmprestimo(emprestimo);

        LocalDate dataMaxima = LocalDate.now().plusMonths(3);

        if (emprestimo.getQuantidadeParcelas() > 60) {

                throw new EmprestimoParcelasExcedida(emprestimo.getQuantidadeParcelas());

             }else if (emprestimo.getDataPrimeiraParcela().isAfter(dataMaxima)) {

            throw new EmprestimoDataPrimeiraParcelaExcedida();

        }else return emprestimoRepository.save(emprestimo);
    }

    //método utilizado para atualizar um emprestimo já casdastrado
    public Emprestimo atualizar(Integer id, Emprestimo emprestimo) throws EmprestimoNaoCadastradoException {

        emprestimoRepository.findById(id).orElseThrow(() -> new EmprestimoNaoCadastradoException(id));
        emprestimo.setId(id);
        return emprestimoRepository.save(emprestimo);
    }

    //método para excluir um emprestimo cadastrado
    public void deletar(Integer id) throws EmprestimoNaoCadastradoException{
        emprestimoRepository.deleteById(id);
    }

    //método para buscar e adicionar um cliente cadastrado na solicitação de emprestimo
    private Emprestimo setClienteEmprestimo (Emprestimo emprestimo) throws ClienteNaoCadastradoException {

        Optional<Cliente> cliente = clienteRepository.findById(emprestimo.getCliente().getId());

        if (cliente.isEmpty()){

            throw new ClienteNaoCadastradoException(emprestimo.getCliente().getId());

            }  else { emprestimo.setCliente(cliente.get());
                        return emprestimo;
             }
    }


}
