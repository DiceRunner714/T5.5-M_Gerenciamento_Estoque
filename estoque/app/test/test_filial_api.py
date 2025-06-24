from fastapi.testclient import TestClient
from main import app

client = TestClient(app)

def test_criar_filial():
    response_empresa = client.post("/empresa/", json={"nome": "Empresa Teste"})
    assert response_empresa.status_code == 200
    empresa_id = response_empresa.json()["id"]

    response_filial = client.post("/filial/", json={
        "nome": "Filial Teste",
        "local": "São Paulo",
        "empresa_id": empresa_id
    })
    assert response_filial.status_code == 200
    json = response_filial.json()
    assert json["nome"] == "Filial Teste"
    assert json["local"] == "São Paulo"
    assert json["empresa_id"] == empresa_id

def test_listar_filiais():
    response = client.get("/filial/")
    assert response.status_code == 200
    assert isinstance(response.json(), list)