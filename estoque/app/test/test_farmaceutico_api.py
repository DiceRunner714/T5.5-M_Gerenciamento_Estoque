from fastapi.testclient import TestClient
from main import app


client = TestClient(app)


def criar_empresa_e_filial():
    empresa = client.post("/empresa/", json={"nome": "Empresa Farma"}).json()
    filial = client.post("/filial/", json={
        "nome": "Filial Farma",
        "local": "Curitiba",
        "empresa_id": empresa["id"]
    }).json()
    return filial["id"]


def test_criar_farmaceutico():
    filial_id = criar_empresa_e_filial()
    response = client.post("/farmaceutico/", json={
        "nome": "Dr. João",
        "crf": "CRF12345",
        "filial_id": filial_id
    })
    assert response.status_code == 200
    json = response.json()
    assert json["nome"] == "Dr. João"
    assert json["crf"] == "CRF12345"
    assert json["filial_id"] == filial_id
    assert json["ativo"] is True


def test_listar_farmaceuticos():
    response = client.get("/farmaceutico/")
    assert response.status_code == 200
    assert isinstance(response.json(), list)


def test_buscar_farmaceutico_por_id():
    filial_id = criar_empresa_e_filial()
    post = client.post("/farmaceutico/", json={
        "nome": "Dr. Maria",
        "crf": "CRF99999",
        "filial_id": filial_id
    }).json()

    response = client.get(f"/farmaceutico/{post['id']}")
    assert response.status_code == 200
    json = response.json()
    assert json["id"] == post["id"]
    assert json["crf"] == "CRF99999"


def test_deletar_farmaceutico():
    filial_id = criar_empresa_e_filial()
    post = client.post("/farmaceutico/", json={
        "nome": "Dr. Delete",
        "crf": "CRF00000",
        "filial_id": filial_id
    }).json()


    response = client.delete(f"/farmaceutico/{post['id']}")
    assert response.status_code == 200
    assert response.json()["mensagem"] == "Farmacêutico removido com sucesso"
