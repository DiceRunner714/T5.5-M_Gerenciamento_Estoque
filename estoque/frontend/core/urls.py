from django.urls import path
from . import views

urlpatterns = [
    path('', views.home, name='home'),
    
    # Empresa URLs
    path('empresas/', views.empresa_list, name='empresa_list'),
    path('empresas/<int:pk>/', views.empresa_detail, name='empresa_detail'),
    path('empresas/nova/', views.empresa_create, name='empresa_create'),
    path('empresas/<int:pk>/deletar/', views.empresa_delete, name='empresa_delete'),
    
    # Filial URLs
    path('filiais/', views.filial_list, name='filial_list'),
    path('filiais/<int:pk>/', views.filial_detail, name='filial_detail'),
    path('filiais/nova/', views.filial_create, name='filial_create'),
    path('filiais/<int:pk>/deletar/', views.filial_delete, name='filial_delete'),
    
    # Produto Químico URLs
    path('produtos/', views.produto_list, name='produto_list'),
    path('produtos/<int:pk>/', views.produto_detail, name='produto_detail'),
    path('produtos/novo/', views.produto_create, name='produto_create'),
    path('produtos/<int:pk>/deletar/', views.produto_delete, name='produto_delete'),
    
    # Farmacêutico URLs
    path('farmaceuticos/', views.farmaceutico_list, name='farmaceutico_list'),
    path('farmaceuticos/<int:pk>/', views.farmaceutico_detail, name='farmaceutico_detail'),
    path('farmaceuticos/novo/', views.farmaceutico_create, name='farmaceutico_create'),
    path('farmaceuticos/<int:pk>/deletar/', views.farmaceutico_delete, name='farmaceutico_delete'),

    # Item URLs
    path('itens/novo/', views.item_create, name='item_create'),
    path('itens/<str:tipo>/<int:pk>/', views.item_detail, name='item_detail'),
    path('itens/<str:tipo>/<int:pk>/deletar/', views.item_delete, name='item_delete'),
]