import { Component } from '@angular/core';
import { Category } from '../models/Category.model';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { CategoryService } from '../category.service';


@Component({
  selector: 'app-admin-category',
  templateUrl: './admin-category.component.html',
  styleUrls: ['./admin-category.component.scss']
})

export class AdminCategoryComponent {
  public selectedCategory: number | null = null;
  categoryForm: FormGroup;
  public categories: Category[] = [];


  public selectCategory(category: number): void {
    this.selectedCategory = this.selectedCategory === category ? null : category;
  }

  private loadCategories(): void {
    this.categoryService.getCategoriesFromDatabase().subscribe(response => {
      this.categories = response;
    });
  }

  public addCategory(): void {
    const newCategoryId = this.getNewCategoryId();
    const newCategory: CategoryToAdd = {
      id: newCategoryId,
      name: this.categoryForm.value.categoryName,
      isParentCategory: true,
      subcategories: [],
      parentId: null
    };
    this.categoryService.addCategory(newCategory).subscribe(() => {
      this.loadCategories();
    });
  }


  public addSubcategory(parentId: number): void {
    const newCategoryId = this.getNewSubcategoryId(parentId);
    const newSubcategory: CategoryToAdd = {
      id: newCategoryId,
      name: this.categoryForm.value.subcategoryName,
      isParentCategory: false,
      subcategories: [],
      parentId: parentId
    };
    this.categoryService.addSubcategory(newSubcategory).subscribe(() => {
      this.loadCategories();
    });
  }

  private getNewCategoryId(): number {
    const maxId = this.categories.reduce((max, category) => Math.max(max, category.id), 0);
    return maxId + 1;
  }

  private getNewSubcategoryId(parentId: number): number {
    const parentCategory = this.categories.find(category => category.id === parentId);
    if (!parentCategory) {
      return -1; // Zwróć -1, jeśli nie znaleziono kategorii nadrzędnej
    }
  
    const subcategoriesCount = parentCategory.subcategories ? parentCategory.subcategories.length : 0;
    return parseInt(`${parentId}0${subcategoriesCount + 1}`);
  }
  
  

  constructor(private router: Router, private formBuilder: FormBuilder, private categoryService: CategoryService) {
    this.categoryForm = this.formBuilder.group({
      categoryName: [''],
      subcategoryName: ['']
    });

    this.loadCategories();
  }
}

export interface CategoryToAdd {
  id: number | null;
  name: string; 
  isParentCategory: boolean;
  subcategories: Subcategory[];
  parentId: number | null;
}

export interface Subcategory {
  id: number;
  name: string;
}
