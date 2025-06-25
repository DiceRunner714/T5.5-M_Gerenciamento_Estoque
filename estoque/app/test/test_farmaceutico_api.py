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
    return empresa["id"], filial["id"]


def test_criar_farmaceutico():
    empresa_id, filial_id = criar_empresa_e_filial()

    response = client.post("/farmaceutico/", json={
        "nome": "Paracetamol",
        "crf": "CRF12345",
        "filial_id": filial_id
    })
    assert response.status_code == 200
    json = response.json()
    assert json["nome"] == "Paracetamol"
    assert json["crf"] == "CRF12345"
    assert json["filial_id"] == filial_id
    assert json["ativo"] is True

    client.delete(f"/farmaceutico/{json['id']}")
    client.delete(f"/filial/{filial_id}")
    client.delete(f"/empresa/{empresa_id}")


def test_listar_farmaceuticos():
    empresa_id, filial_id = criar_empresa_e_filial()
    farmaceutico = client.post("/farmaceutico/", json={
        "nome": "Dr. Lista",
        "crf": "CRFLIST",
        "filial_id": filial_id
    }).json()

    response = client.get("/farmaceutico/")
    assert response.status_code == 200
    assert isinstance(response.json(), list)
    assert any(f["id"] == farmaceutico["id"] for f in response.json())

    client.delete(f"/farmaceutico/{farmaceutico['id']}")
    client.delete(f"/filial/{filial_id}")
    client.delete(f"/empresa/{empresa_id}")


def test_buscar_farmaceutico_por_id():
    empresa_id, filial_id = criar_empresa_e_filial()
    post = client.post("/farmaceutico/", json={
        "nome": "Rem Dor de cabeça",
        "crf": "CRF99999",
        "filial_id": filial_id
    }).json()

    response = client.get(f"/farmaceutico/{post['id']}")
    assert response.status_code == 200
    json = response.json()
    assert json["id"] == post["id"]
    assert json["crf"] == "CRF99999"

    client.delete(f"/farmaceutico/{post['id']}")
    client.delete(f"/filial/{filial_id}")
    client.delete(f"/empresa/{empresa_id}")


def test_deletar_farmaceutico():
    empresa_id, filial_id = criar_empresa_e_filial()
    post = client.post("/farmaceutico/", json={
        "nome": "Dr. Delete",
        "crf": "CRF00000",
        "filial_id": filial_id
    }).json()

    response = client.delete(f"/farmaceutico/{post['id']}")
    assert response.status_code == 200
    assert response.json()["mensagem"] == "Farmacêutico removido com sucesso"

    client.delete(f"/filial/{filial_id}")
    client.delete(f"/empresa/{empresa_id}")
