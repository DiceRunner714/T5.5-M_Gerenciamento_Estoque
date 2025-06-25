from fastapi.testclient import TestClient
from main import app
import sys
import os
sys.path.insert(0, os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))


client = TestClient(app)


def test_criar_empresa():
    response = client.post("/empresa/", json={"nome": "Empresa Teste"})
    assert response.status_code == 200
    data = response.json()
    assert "id" in data
    assert data["nome"] == "Empresa Teste"


def test_listar_empresas():
    response = client.get("/empresa/")
    assert response.status_code == 200
    data = response.json()
    assert isinstance(data, list)
    assert len(data) > 0


def test_buscar_empresa_por_id():
    post = client.post("/empresa/", json={"nome": "Empresa Consulta"})
    id_empresa = post.json()["id"]

    response = client.get(f"/empresa/{id_empresa}")
    assert response.status_code == 200
    data = response.json()
    assert data["id"] == id_empresa
    assert data["nome"] == "Empresa Consulta"


def test_deletar_empresa():
    post = client.post("/empresa/", json={"nome": "Empresa Delete"})
    id_empresa = post.json()["id"]

    delete = client.delete(f"/empresa/{id_empresa}")
    assert delete.status_code == 200

    get = client.get(f"/empresa/{id_empresa}")
    assert get.status_code == 404
