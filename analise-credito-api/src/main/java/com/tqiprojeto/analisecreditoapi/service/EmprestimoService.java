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


/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see EmprestimoRepository
 * @see ClienteRepository
 * @since Release 1.0
 */
@Service
public class EmprestimoService {

    private EmprestimoRepository emprestimoRepository;

    private ClienteRepository clienteRepository;


    /**
     * Construtor da classe
     *
     * @param emprestimoRepository      Objeto de acesso da classe EmprestimoRepository
     * @param clienteRepository         Objeto de acesso da classe ClienteRepository (verificação dos emails para tela de login)
     */
    @Autowired
    public EmprestimoService(EmprestimoRepository emprestimoRepository, ClienteRepository clienteRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.clienteRepository = clienteRepository;
    }

    /**
     * Método GET para listar todos os emprestimos cadastrados de todos os clientes
     *
     * @return lista de todos os emprestimos cadastrados
     */
    public List<Emprestimo> listarTodosEmprestimos (){
        return emprestimoRepository.findAll();
    }

    /**
     * Método GET para listagem simples dos emprestimos, retornando os campos id, valor e quantidade de parcelas do empréstimo.
     *
     * Essa listagem é específica de um determinado cliente, sendo informado o id do CLIENTE para a busca
     * dos emprestimos feitos por ele.
     *
     * @param id    ID do Cliente que será informado para realizar a busca dos emprestimos feitos por ele
     * @return      Lista Simples com os campos id, valor e quantidade de parcelas da classe Emprestimo (vinculado ao id do cliente)
     * @throws EmprestimoNaoCadastradoException     Se não houver emprestimos cadastrados
     */
    public List<EmprestimoRetornoSimples> listarSimples(Integer id) throws EmprestimoNaoCadastradoException {

        List<EmprestimoRetornoSimples> listaSimples = emprestimoRepository.listaSimples(id);

        if (listaSimples.isEmpty()){
           throw new EmprestimoNaoCadastradoException(id);}
        else {
        return listaSimples;
        }

    }

    /**
     * Método GET para listagem mais detalhada dos emprestimos, retornando os campos
     * id, valor, quantidade de parcelas, data da primeira parcela  do empréstimo e e-mail e renda do cliente vinculado aquele emprestimo.
     *
     * Essa listagem é específica de um determinado cliente, sendo informado o id do CLIENTE para a busca
     * dos emprestimos feitos por ele.
     *
     * @param id    ID do Cliente que será informado para realizar a busca dos emprestimos feitos por ele
     *
     * @return      Lista Detalhada com os campos id, valor, quantidade de parcelas, data da primeira parcela  do empréstimo
     * e e-mail e renda do cliente vinculado aquele emprestimo
     *
     * @throws EmprestimoNaoCadastradoException     Se não houver emprestimos cadastrados
     */
   public List<EmprestimoRetornoDetalhado> listarEmprestimoDetalhado (Integer id) throws EmprestimoNaoCadastradoException {

       List<EmprestimoRetornoDetalhado> listaDetalhada = emprestimoRepository.listarEmprestimoDetalhado(id);

        if (listaDetalhada.isEmpty()){
            throw new EmprestimoNaoCadastradoException(id);
        }else{
        return listaDetalhada;}
    }

    /**
     * Lista de um emprestimo buscando por ID
     *
     * @param id    Código do emprestimo
     * @return      Lista do emprestimo referente ao ID informado
     * @throws EmprestimoNaoCadastradoException     Se não houver emprestimos cadastrados
     */
       public Emprestimo buscarPorId(Integer id) throws EmprestimoNaoCadastradoException {

        Emprestimo emprestimo = emprestimoRepository.findById(id).orElseThrow(() -> new EmprestimoNaoCadastradoException(id));
        return emprestimo ;
    }


    /**
     * Cadastro de emprestimos
     *
     * @param emprestimo    Payload dos dados para cadastro do emprestimo
     * @return              Retorna os campos do emprestimo criado
     * @throws EmprestimoParcelasExcedida       Se a quantidade de parcelas forem superior a 60
     * @throws ParseException                   Se o formato da data da primeira parcela for inválido
     * @throws EmprestimoDataPrimeiraParcelaExcedida    Se a data da primeira parcela for superior a 3 meses da data da solicitação
     * @throws ClienteNaoCadastradoException            Se não houver cliente cadastrado,
     */
    public Emprestimo solicitar(Emprestimo emprestimo) throws EmprestimoParcelasExcedida, ParseException, EmprestimoDataPrimeiraParcelaExcedida, ClienteNaoCadastradoException {

        setClienteEmprestimo(emprestimo);

        LocalDate dataMaxima = LocalDate.now().plusMonths(3);

        if (emprestimo.getQuantidadeParcelas() > 60) {

                throw new EmprestimoParcelasExcedida(emprestimo.getQuantidadeParcelas());

             }else if (emprestimo.getDataPrimeiraParcela().isAfter(dataMaxima)) {

            throw new EmprestimoDataPrimeiraParcelaExcedida();

        }else return emprestimoRepository.save(emprestimo);
    }

    /**
     * Atualizar algum dado do emprestimo cadastrado
     *
     * @param id                 Id do emprestimo
     * @param emprestimo        Payload dos dados para cadastro do emprestimo
     * @return                  Retorna os campos do objeto atualizado
     * @throws EmprestimoNaoCadastradoException Se não houver nenhum emprestimo cadastrado com aquele ID.
     * @throws ClienteNaoCadastradoException    Se não houver nenhum cliente cadastrado
     * @throws EmprestimoParcelasExcedida       Se exceder a quantidade maxima de 60 parcelas
     */
    public Emprestimo atualizar(Integer id, Emprestimo emprestimo) throws EmprestimoNaoCadastradoException, ClienteNaoCadastradoException, EmprestimoDataPrimeiraParcelaExcedida {

        emprestimoRepository.findById(id).orElseThrow(() -> new EmprestimoNaoCadastradoException(id));

        emprestimo.setId(id);
        setClienteEmprestimo(emprestimo);

        LocalDate dataMaxima = LocalDate.now().plusMonths(3);

        if (emprestimo.getQuantidadeParcelas() > 60) {

            throw new EmprestimoParcelasExcedida(emprestimo.getQuantidadeParcelas());

        }else if (emprestimo.getDataPrimeiraParcela().isAfter(dataMaxima)) {

            throw new EmprestimoDataPrimeiraParcelaExcedida();

        }else return emprestimoRepository.save(emprestimo);

    }

    /**
     * Excluir um emprestimo
     *
     * @param id    ID do emprestimo a ser excluido
     * @throws EmprestimoNaoCadastradoException  Se não houver nenhum emprestimo cadastrado com aquele ID.
     */
    public void deletar(Integer id) throws EmprestimoNaoCadastradoException{
        emprestimoRepository.deleteById(id);
    }

    /**
     * Buscar e adicionar um cliente cadastrado na solicitação de emprestimo
     *
     * @param emprestimo        Objeto com as informações do emprestimo
     * @return                  Objeto emprestimo com o cliente vinculado
     * @throws ClienteNaoCadastradoException        Se não houver cliente cadastrado
     */
    private Emprestimo setClienteEmprestimo (Emprestimo emprestimo) throws ClienteNaoCadastradoException {

        Optional<Cliente> cliente = clienteRepository.findById(emprestimo.getCliente().getId());

        if (cliente.isEmpty()){

            throw new ClienteNaoCadastradoException(emprestimo.getCliente().getId());

            }  else { emprestimo.setCliente(cliente.get());
                        return emprestimo;
             }
    }


}
