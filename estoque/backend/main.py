from fastapi import FastAPI
from config.database import Base, engine
from view import empresa_routes
from view import filial_routes
from view import produto_quimico_routes
from view import farmaceutico_routes

app = FastAPI()

app.include_router(farmaceutico_routes.router)
app.include_router(produto_quimico_routes.router)
app.include_router(filial_routes.router)
app.include_router(empresa_routes.router)


@app.on_event("startup")
def on_startup():
    Base.metadata.create_all(bind=engine)
