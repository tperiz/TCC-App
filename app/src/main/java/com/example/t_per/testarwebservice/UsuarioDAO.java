package com.example.t_per.testarwebservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by t_per on 21/12/2016.
 */

public class UsuarioDAO {

    private static final String URL = "http://192.168.1.6:8080/ExemploWS/services/UsuarioDao?wsdl";
    private static final String NAMESPACE = "http://exemploWS.videoaulazeni.com.br";
    private static final String inserir = "inserirUsuario";
    private static final String excluir = "excluirUsuario";
    private static final String atualizar = "atualizarUsuario";
    private static final String buscarTodosUsu = "buscarTodosUsuarios";
    private static final String buscarTodosCri = "buscarTodosCriterios";
    private static final String buscarTodosHie = "buscarTodasHierarquias";
    private static final String buscarTodasAlt = "buscarTodasAlternativas";
    private static final String buscarTodosSub = "buscarTodosSubCriterios";
    private static final String buscarPorId = "buscarUsuarioPorId";

    public boolean inserirUsuario(Usuario usuario){

        SoapObject inserirUsuario = new SoapObject(NAMESPACE,inserir);
        SoapObject user = new SoapObject(NAMESPACE,"usuario");
        user.addProperty("id", usuario.getId());
        user.addProperty("idade", usuario.getIdade());
        user.addProperty("nome", usuario.getNome());
        inserirUsuario.addSoapObject(user);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(inserirUsuario);
        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + inserir, envelope);
            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();
            return Boolean.parseBoolean(resposta.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean excluirUsuario(int id){

        return excluirUsuario(new Usuario(id,"",0));

    }

    public boolean excluirUsuario(Usuario usuario){

        return true;

    }

    public ArrayList<Usuario> buscarTodosUsuarios(){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();

        SoapObject buscarUsuarios = new SoapObject(NAMESPACE,buscarTodosUsu);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscarUsuarios);
        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + buscarTodosUsu, envelope);
            Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();
            for (SoapObject soapObject: resposta) {
                Usuario user = new Usuario();
                user.setNome(soapObject.getProperty("nome").toString());
                lista.add(user);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }


    public ArrayList<Criterio> buscarTodosCriterios(int hierarquia){
        ArrayList<Criterio> lista = new ArrayList<Criterio>();
        SoapObject buscarCriterios = new SoapObject(NAMESPACE,buscarTodosCri);
        buscarCriterios.addProperty("hierarquia", hierarquia);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscarCriterios);
        envelope.implicitTypes = true;


        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + buscarTodosCri, envelope);
            Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();
            for (SoapObject soapObject: resposta) {
                Criterio cri = new Criterio();
                cri.setIdcirterio(Integer.parseInt(soapObject.getProperty("idcriterio").toString()));
                cri.setHierarquia(Integer.parseInt(soapObject.getProperty("hierarquia").toString()));
                cri.setNome(soapObject.getProperty("nome").toString());
                cri.setDescricao(soapObject.getProperty("descricao").toString());
                lista.add(cri);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }



    public ArrayList<Hierarquia> buscarTodasHierarquias(){
        ArrayList<Hierarquia> lista = new ArrayList<Hierarquia>();

        SoapObject buscarHierarquias = new SoapObject(NAMESPACE,buscarTodosHie);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscarHierarquias);
        envelope.implicitTypes = true;
        System.out.println("aaaaaaaaaa");
        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + buscarTodosHie, envelope);
            Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();
            for (SoapObject soapObject: resposta) {
                Hierarquia hierarq = new Hierarquia();
                hierarq.setNome(soapObject.getProperty("nome").toString());
                hierarq.setTipomedia(soapObject.getProperty("tipomedia").toString());
                hierarq.setObjetivo(soapObject.getProperty("objetivo").toString());
                hierarq.setCalculo(soapObject.getProperty("calculo").toString());
                hierarq.setIdhierarquia(Integer.parseInt(soapObject.getProperty("idhierarquia").toString()));

                lista.add(hierarq);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }

    public ArrayList<Alternativa> buscarTodasAlternativas(int criterio, int subcriterio, int hierarquia){
        ArrayList<Alternativa> lista = new ArrayList<Alternativa>();

        SoapObject buscarAlternativas = new SoapObject(NAMESPACE,buscarTodasAlt);
        buscarAlternativas.addProperty("criterio", criterio);
        buscarAlternativas.addProperty("subcriterio", subcriterio);
        buscarAlternativas.addProperty("hierarquia", hierarquia);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscarAlternativas);
        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + buscarTodasAlt, envelope);
            Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();
            for (SoapObject soapObject: resposta) {
                Alternativa alt = new Alternativa();
                alt.setIdcriterio(Integer.parseInt(soapObject.getProperty("idcriterio").toString()));
                alt.setIdsubcriterio(Integer.parseInt(soapObject.getProperty("idsubcriterio").toString()));
                alt.setNome(soapObject.getProperty("nome").toString());
                alt.setDescricao(soapObject.getProperty("descricao").toString());
                alt.setHierarquia(Integer.parseInt(soapObject.getProperty("hierarquia").toString()));
                lista.add(alt);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }

    public ArrayList<SubCriterio> buscarTodosSubCriterios(int criterio, int hierarquia){
        ArrayList<SubCriterio> lista = new ArrayList<SubCriterio>();

        SoapObject buscarSubCriterios = new SoapObject(NAMESPACE,buscarTodosSub);
        buscarSubCriterios.addProperty("criterio", criterio);
        buscarSubCriterios.addProperty("hierarquia", hierarquia);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(buscarSubCriterios);
        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + buscarTodosSub, envelope);
            Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();
            for (SoapObject soapObject: resposta) {
                SubCriterio sub = new SubCriterio();
                sub.setIdcriterio(Integer.parseInt(soapObject.getProperty("idcriterio").toString()));
                sub.setIdsubcriterio(Integer.parseInt(soapObject.getProperty("idsubcriterio").toString()));
                sub.setNome(soapObject.getProperty("nome").toString());
                sub.setDescricao(soapObject.getProperty("descricao").toString());
                sub.setIdhierarquia(Integer.parseInt(soapObject.getProperty("idhierarquia").toString()));
                lista.add(sub);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }



    public Usuario buscarUsuarioPorId(int id){
        Usuario user = null;

        return user;
    }

    public boolean atualizarUsuario(Usuario usuario){


        return true;

    }




}
