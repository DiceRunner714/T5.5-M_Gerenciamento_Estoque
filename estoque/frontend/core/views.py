from django.shortcuts import render, redirect
from django.contrib import messages
from django.views.generic import ListView
from django.http import Http404
from .forms import EmpresaForm, FilialForm, ProdutoQuimicoForm, FarmaceuticoForm
from .services import EmpresaService, FilialService, ProdutoQuimicoService, FarmaceuticoService

def home(request):
    """Home page with navigation links"""
    return render(request, 'home.html')

# Empresa Views
def empresa_list(request):
    service = EmpresaService()
    empresas = service.listar_empresas()
    return render(request, 'empresa/list.html', {'empresas': empresas})

def empresa_detail(request, pk):
    service = EmpresaService()
    empresa = service.buscar_empresa(pk)
    if not empresa:
        raise Http404("Empresa não encontrada")
    
    estoque = service.estoque_total(pk)
    return render(request, 'empresa/detail.html', {
        'empresa': empresa,
        'estoque': estoque
    })

def empresa_create(request):
    if request.method == 'POST':
        form = EmpresaForm(request.POST)
        if form.is_valid():
            service = EmpresaService()
            data = {'nome': form.cleaned_data['nome']}
            result = service.criar_empresa(data)
            if result:
                messages.success(request, 'Empresa criada com sucesso!')
                return redirect('empresa_list')
            else:
                messages.error(request, 'Erro ao criar empresa.')
    else:
        form = EmpresaForm()
    
    return render(request, 'empresa/create.html', {'form': form})

def empresa_delete(request, pk):
    if request.method == 'POST':
        service = EmpresaService()
        if service.deletar_empresa(pk):
            messages.success(request, 'Empresa deletada com sucesso!')
        else:
            messages.error(request, 'Erro ao deletar empresa.')
        return redirect('empresa_list')
    
    service = EmpresaService()
    empresa = service.buscar_empresa(pk)
    if not empresa:
        raise Http404("Empresa não encontrada")
    
    return render(request, 'empresa/delete.html', {'empresa': empresa})

# Filial Views
def filial_list(request):
    service = FilialService()
    empresa_id = request.GET.get('empresa_id')

    filiais = service.listar_filiais(empresa_id=empresa_id)
    return render(request, 'filial/list.html', {
        'filiais': filiais,
        'empresa_id': empresa_id
    })

def filial_detail(request, pk):
    service = FilialService()
    filial = service.buscar_filial(pk)
    if not filial:
        raise Http404("Filial não encontrada")

    mostrar_zerados = request.GET.get('zerados') == 'true'

    if mostrar_zerados:
        itens_zerados = service.itens_zerados(pk)
        return render(request, 'filial/detail.html', {
            'filial': filial,
            'mostrar_zerados': True,
            'itens_zerados': itens_zerados.get('itens_zerados', [])
        })
    else:
        estoque = service.estoque_filial(pk)
        return render(request, 'filial/detail.html', {
            'filial': filial,
            'mostrar_zerados': False,
            'estoque': estoque
        })


def filial_create(request):
    empresa_id = request.GET.get('empresa_id')

    if request.method == 'POST':
        form = FilialForm(request.POST)
        if form.is_valid():
            service = FilialService()
            data = {
                'nome': form.cleaned_data['nome'],
                'local': form.cleaned_data['local'],
                'empresa_id': form.cleaned_data['empresa_id']
            }
            result = service.criar_filial(data)
            if result:
                messages.success(request, 'Filial criada com sucesso!')
                return redirect('filial_list')
            else:
                messages.error(request, 'Erro ao criar filial.')
    else:
        initial_data = {}
        if empresa_id:
            initial_data['empresa_id'] = empresa_id
        form = FilialForm(initial=initial_data)
    
    return render(request, 'filial/create.html', {'form': form})

def filial_delete(request, pk):
    if request.method == 'POST':
        service = FilialService()
        if service.deletar_filial(pk):
            messages.success(request, 'Filial deletada com sucesso!')
        else:
            messages.error(request, 'Erro ao deletar filial.')
        return redirect('filial_list')
    
    service = FilialService()
    filial = service.buscar_filial(pk)
    if not filial:
        raise Http404("Filial não encontrada")
    
    return render(request, 'filial/delete.html', {'filial': filial})

# Produto Químico Views
def produto_list(request):
    service = ProdutoQuimicoService()
    produtos = service.listar_produtos()
    return render(request, 'produto/list.html', {'produtos': produtos})

def produto_detail(request, pk):
    service = ProdutoQuimicoService()
    produto = service.buscar_produto(pk)
    if not produto:
        raise Http404("Produto não encontrado")
    
    return render(request, 'produto/detail.html', {'produto': produto})

def produto_create(request):
    if request.method == 'POST':
        form = ProdutoQuimicoForm(request.POST)
        if form.is_valid():
            service = ProdutoQuimicoService()
            data = {
                'nome': form.cleaned_data['nome'],
                'categoria': form.cleaned_data['categoria'],
                'valor': float(form.cleaned_data['valor']),
                'quantidade': form.cleaned_data['quantidade'],
                'toxicidade': form.cleaned_data['toxicidade'],
                'filial_id': form.cleaned_data['filial_id']
            }
            result = service.criar_produto(data)
            if result:
                messages.success(request, 'Produto criado com sucesso!')
                return redirect('produto_list')
            else:
                messages.error(request, 'Erro ao criar produto.')
    else:
        form = ProdutoQuimicoForm()
    
    return render(request, 'produto/create.html', {'form': form})

def produto_delete(request, pk):
    if request.method == 'POST':
        service = ProdutoQuimicoService()
        if service.deletar_produto(pk):
            messages.success(request, 'Produto deletado com sucesso!')
        else:
            messages.error(request, 'Erro ao deletar produto.')
        return redirect('produto_list')
    
    service = ProdutoQuimicoService()
    produto = service.buscar_produto(pk)
    if not produto:
        raise Http404("Produto não encontrado")
    
    return render(request, 'produto/delete.html', {'produto': produto})

# Farmacêutico Views
def farmaceutico_list(request):
    service = FarmaceuticoService()
    farmaceuticos = service.listar_farmaceuticos()
    return render(request, 'farmaceutico/list.html', {'farmaceuticos': farmaceuticos})

def farmaceutico_detail(request, pk):
    service = FarmaceuticoService()
    farmaceutico = service.buscar_farmaceutico(pk)
    if not farmaceutico:
        raise Http404("Farmacêutico não encontrado")
    
    return render(request, 'farmaceutico/detail.html', {'farmaceutico': farmaceutico})

def farmaceutico_create(request):
    if request.method == 'POST':
        form = FarmaceuticoForm(request.POST)
        if form.is_valid():
            service = FarmaceuticoService()
            data = {
                'nome': form.cleaned_data['nome'],
                'categoria': form.cleaned_data['categoria'],
                'quantidade': form.cleaned_data['quantidade'],
                'valor': float(form.cleaned_data['valor']),
                'tarja': form.cleaned_data['tarja'],
                'composicao': form.cleaned_data['composicao'],
                'retencao_de_receita': form.cleaned_data['retencao_de_receita'],
                'generico': form.cleaned_data['generico'],
                'receita': form.cleaned_data['receita'],
                'filial_id': form.cleaned_data['filial_id']
            }
            result = service.criar_farmaceutico(data)
            if result:
                messages.success(request, 'Farmacêutico criado com sucesso!')
                return redirect('farmaceutico_list')
            else:
                messages.error(request, 'Erro ao criar farmacêutico.')
    else:
        form = FarmaceuticoForm()
    
    return render(request, 'farmaceutico/create.html', {'form': form})

def farmaceutico_delete(request, pk):
    if request.method == 'POST':
        service = FarmaceuticoService()
        if service.deletar_farmaceutico(pk):
            messages.success(request, 'Farmacêutico deletado com sucesso!')
        else:
            messages.error(request, 'Erro ao deletar farmacêutico.')
        return redirect('farmaceutico_list')
    
    service = FarmaceuticoService()
    farmaceutico = service.buscar_farmaceutico(pk)
    if not farmaceutico:
        raise Http404("Farmacêutico não encontrado")
    
    return render(request, 'farmaceutico/delete.html', {'farmaceutico': farmaceutico})

# Item Views
def item_create(request):
    tipo = request.GET.get('tipo', 'farmaceutico')
    filial_id = request.GET.get('filial_id') or request.POST.get('filial_id')
    
    if request.method == 'POST':
        tipo = request.POST.get('tipo', 'farmaceutico')
        if tipo == 'produto':
            form = ProdutoQuimicoForm(request.POST)
            if form.is_valid():
                service = ProdutoQuimicoService()
                data = {
                    'nome': form.cleaned_data['nome'],
                    'categoria': form.cleaned_data['categoria'],
                    'valor': float(form.cleaned_data['valor']),
                    'quantidade': form.cleaned_data['quantidade'],
                    'toxicidade': form.cleaned_data['toxicidade'],
                    'filial_id': form.cleaned_data['filial_id']
                }
                result = service.criar_produto(data)
                if result:
                    messages.success(request, 'Produto criado com sucesso!')
                    return redirect('filial_detail', pk=filial_id)
                else:
                    messages.error(request, 'Erro ao criar produto.')
        else:
            form = FarmaceuticoForm(request.POST)
            if form.is_valid():
                service = FarmaceuticoService()
                data = {
                    'nome': form.cleaned_data['nome'],
                    'categoria': form.cleaned_data['categoria'],
                    'quantidade': form.cleaned_data['quantidade'],
                    'valor': float(form.cleaned_data['valor']),
                    'tarja': form.cleaned_data['tarja'],
                    'composicao': form.cleaned_data['composicao'],
                    'retencao_de_receita': form.cleaned_data['retencao_de_receita'],
                    'generico': form.cleaned_data['generico'],
                    'receita': form.cleaned_data['receita'],
                    'filial_id': form.cleaned_data['filial_id']
                }
                result = service.criar_farmaceutico(data)
                if result:
                    messages.success(request, 'Farmacêutico criado com sucesso!')
                    return redirect('filial_detail', pk=filial_id)
                else:
                    messages.error(request, 'Erro ao criar farmacêutico.')
    else:
        if tipo == 'produto':
            form = ProdutoQuimicoForm(initial={'filial_id': filial_id})
        else:
            form = FarmaceuticoForm(initial={'filial_id': filial_id})

    contexto = {
        'tipo': tipo,
        'filial_id': filial_id,
        'produto_form': form if tipo == 'produto' else None,
        'farmaceutico_form': form if tipo != 'produto' else None,
    }
    return render(request, 'item/create.html', contexto)
