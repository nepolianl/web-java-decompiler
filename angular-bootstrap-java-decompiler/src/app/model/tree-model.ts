export class TreeModel {
    name: string;
    title: string;
    type: string;
    parent: string;
    expand: boolean;
    select: boolean;
    nodes: [];

    constructor(name?: string, title?: string, type?: string, parent?: string) {
        this.name = name;
        this.title = title;
        this.type = type;
        this.parent = parent;
    }
}
